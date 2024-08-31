import json
import boto3
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity


def get_user_profile(user_email):
    dynamodb = boto3.resource('dynamodb')
    table = dynamodb.Table('UserProfile-3ai2clc5pzfchiy2fvaf5iqxwy-dev')
    response = table.get_item(Key={'email': user_email})
    return response.get('Item')


def get_all_products():
    dynamodb = boto3.resource('dynamodb')
    table = dynamodb.Table('SkinCareProduct-3ai2clc5pzfchiy2fvaf5iqxwy-dev')
    response = table.scan()
    return response.get('Items')


def get_filtered_products(skin_type, product_type, notable_effects_filter):
    dynamodb = boto3.resource('dynamodb')
    table = dynamodb.Table('SkinCareProduct-3ai2clc5pzfchiy2fvaf5iqxwy-dev')
    
    # Scan with Filter Expressions
    response = table.scan(
        FilterExpression=Attr('skintype').contains(skin_type) &
                         Attr('productType').eq(product_type) &
                         Attr('notableEffects').contains(notable_effects_filter)
    )
    return response.get('Items')


# Function to generate product recommendations using TF-IDF and cosine similarity
def recommend_products(user_profile, filtered_products):
    
    # Extract user preferences from the profile
    user_skin_type = user_profile.get('skintype', '')
    user_notable_effects = user_profile.get('notableEffects', [])
    
    # Filter products by the user's skin type
    suitable_products = [p for p in filtered_products if user_skin_type in p.get('skintype', [])]

    # Prepare product descriptions for TF-IDF
    product_descriptions = [' '.join(p.get('notableEffects', [])) for p in suitable_products]
    
    # Initialize TF-IDF vectorizer and calculate the matrix
    vectorizer = TfidfVectorizer()
    tfidf_matrix = vectorizer.fit_transform(product_descriptions)
    
    # Calculate cosine similarity between user preferences and products
    user_profile_vector = vectorizer.transform([' '.join(user_notable_effects)])
    cosine_similarities = cosine_similarity(user_profile_vector, tfidf_matrix)
    
    # Get the top 5 product recommendations
    top_indices = cosine_similarities.argsort()[0][-5:][::-1]
    recommendations = [suitable_products[i]['productName'] for i in top_indices]
    
    # Return the recommended product names
    return recommendations



def update_user_profile(user_email, recommendations):
   
    # Initialize DynamoDB resource
    dynamodb = boto3.resource('dynamodb')
    
    # Reference the UserProfile table
    table = dynamodb.Table('UserProfile-3ai2clc5pzfchiy2fvaf5iqxwy-dev')
    
    # Update the user's profile with the list of recommended products
    table.update_item(
        Key={'email': user_email},
        UpdateExpression="set recommendedProducts = :r",
        ExpressionAttributeValues={':r': recommendations}
    )




# This Lambda function processes an HTTP request to generate and return product recommendations for a user:

# It extracts the user's email from the request.
# Retrieves the user's profile from DynamoDB.
# Filters products based on the user's preferences.
# Generates product recommendations.
# Updates the user's profile with these recommendations.
# Returns the recommendations as a JSON response.
# Main Lambda handler function
def handler(event, context):
    # Extract the user's email from the event data
    user_email = event['queryStringParameters']['email']
    
    # Fetch the user's profile from DynamoDB
    user_profile = get_user_profile(user_email)
    
    # Extract relevant user information for filtering products
    skin_type = user_profile.get('skintype', '')
    product_type = user_profile.get('productType', '')
    notable_effects = user_profile.get('notableEffects', [])
    
    # Fetch the products filtered by the user's preferences
    products = get_filtered_products(skin_type, product_type, ' '.join(notable_effects))
    
    # Generate product recommendations based on the user's profile
    recommendations = recommend_products(user_profile, products)
    
    # Update the user's profile in DynamoDB with the recommendations
    update_user_profile(user_email, recommendations)
    
    # Return a successful response with the recommendations
    return {
        'statusCode': 200,
        'headers': {
            'Access-Control-Allow-Headers': '*',
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Methods': 'OPTIONS,POST,GET'
        },
        'body': json.dumps({'recommendations': recommendations})
    }