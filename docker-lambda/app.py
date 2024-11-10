import json
import boto3
import numpy as np
import cv2
import tensorflow as tf
import pickle
from tensorflow.keras.applications.resnet50 import preprocess_input
import PIL.Image as Image


# # Constants
# IMAGE_WIDTH = 224
# IMAGE_HEIGHT = 224
# IMAGE_SHAPE = (IMAGE_WIDTH, IMAGE_HEIGHT)
#
# # Load the model
# model = tf.keras.models.load_model('/var/task/ResNet50_model.h5')
#
# # Load the label encoder
# with open('/var/task/ResNet50_label_encoder.pkl', 'rb') as f:
#     label_encoder = pickle.load(f)
#
# # Initialize S3 resource
# s3 = boto3.resource('s3')
#
# def lambda_handler(event, context):
#     # Get the bucket and key from the event
#     bucket_name = event['Records'][0]['s3']['bucket']['name']
#     key = event['Records'][0]['s3']['object']['key']
#
#     # Read and preprocess the image
#     img = read_image_from_bucket(key, bucket_name)
#     preprocessed_image = preprocess_image_for_lambda(img)
#
#     # Make a prediction
#     predictions = model.predict(preprocessed_image)
#
#     # Log the raw prediction probabilities
#     print(f'Raw prediction probabilities: {predictions[0]}')
#
#     # Get the predicted class
#     predicted_class = label_encoder.inverse_transform([np.argmax(predictions)])[0]
#
#     # Log the prediction details
#     print(f'ImageName: {key}, Predicted class: {predicted_class}')
#
#     # Convert probabilities to native Python float
#     probabilities_with_labels = {label: float(prob) for label, prob in zip(label_encoder.classes_, predictions[0])}
#
#     return {
#         'statusCode': 200,
#         'body': json.dumps({
#             'image': key,
#             'predicted_class': predicted_class,
#             'probabilities': probabilities_with_labels
#         })
#     }
#
# def read_image_from_bucket(key, bucket_name):
#     # Read an image from the specified S3 bucket and key
#     bucket = s3.Bucket(bucket_name)
#     obj = bucket.Object(key)
#     response = obj.get()
#     return Image.open(response['Body'])
#
# def preprocess_image_for_lambda(image):
#     # Convert PIL image to OpenCV format (RGB to BGR conversion is needed because OpenCV uses BGR by default)
#     img = np.array(image)
#     img = cv2.cvtColor(img, cv2.COLOR_RGB2BGR)
#
#     # Resize the image to 224x224 pixels
#     img = cv2.resize(img, (224, 224))
#
#     # Add batch dimension and apply ResNet50-specific preprocessing
#     img_array = np.expand_dims(img, axis=0)
#     img_array = preprocess_input(img_array)
#
#     return img_array


import json
import numpy as np
import tensorflow as tf
import pickle
import boto3
from PIL import Image
import cv2
from tensorflow.keras.applications.resnet50 import preprocess_input

# Constants
IMAGE_WIDTH = 224
IMAGE_HEIGHT = 224
IMAGE_SHAPE = (IMAGE_WIDTH, IMAGE_HEIGHT)

# Load the model
model = tf.keras.models.load_model('/var/task/ResNet50_model.h5')

# Load the label encoder
with open('/var/task/ResNet50_label_encoder.pkl', 'rb') as f:
    label_encoder = pickle.load(f)

# Initialize S3 resource
s3 = boto3.resource('s3')

def lambda_handler(event, context):
    # Get the bucket and key from the event
    bucket_name = event['Records'][0]['s3']['bucket']['name']
    key = event['Records'][0]['s3']['object']['key']

    # Read and preprocess the image
    img = read_image_from_bucket(key, bucket_name)
    preprocessed_image = preprocess_image_for_lambda(img)

    # Make a prediction
    predictions = model.predict(preprocessed_image)

    # Get the predicted class and its probability
    predicted_class_index = np.argmax(predictions[0])
    predicted_class = label_encoder.inverse_transform([predicted_class_index])[0]
    max_probability = predictions[0][predicted_class_index]

    # Determine severity
    severity = "heavy" if max_probability > 0.7 else "light"

    # Log prediction details in CloudWatch
    print(f'ImageName: {key}')
    print(f'Predicted class: {predicted_class}')
    print(f'Severity: {severity}')
    print(f'Raw prediction probabilities: {predictions[0]}')

    # Structure the response with probabilities and severity
    probabilities_with_labels = {
        label: float(prob) for label, prob in zip(label_encoder.classes_, predictions[0])
    }

    return {
        'statusCode': 200,
        'body': json.dumps({
            'image': key,
            'predicted_class': predicted_class,
            'severity': severity,
            'probabilities': probabilities_with_labels
        })
    }

def read_image_from_bucket(key, bucket_name):
    # Read an image from the specified S3 bucket and key
    bucket = s3.Bucket(bucket_name)
    obj = bucket.Object(key)
    response = obj.get()
    return Image.open(response['Body'])

def preprocess_image_for_lambda(image):
    # Convert PIL image to OpenCV format (RGB to BGR conversion is needed because OpenCV uses BGR by default)
    img = np.array(image)
    img = cv2.cvtColor(img, cv2.COLOR_RGB2BGR)

    # Resize the image to 224x224 pixels
    img = cv2.resize(img, (224, 224))

    # Add batch dimension and apply ResNet50-specific preprocessing
    img_array = np.expand_dims(img, axis=0)
    img_array = preprocess_input(img_array)

    return img_array

