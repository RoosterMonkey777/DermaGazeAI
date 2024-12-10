# Use the AWS Lambda Python 3.8 base image
FROM public.ecr.aws/lambda/python:3.8

# Install necessary system packages for OpenCV
RUN yum -y install tar gzip zlib freetype-devel gcc libjpeg-turbo-devel libpng-devel libtiff-devel libwebp-devel && yum clean all

# Install OpenCV from source (this may take a while)
RUN pip install --upgrade pip
RUN pip install opencv-python-headless==4.5.5.64

# Copy and install Python dependencies
COPY requirements.txt ./
RUN python3.8 -m pip install -r requirements.txt

# Copy your application code to the container
COPY app.py ./

# Copy the model and label encoder files to the Lambda's default directory
COPY ResNet50_model.h5 /var/task/ResNet50_model.h5
COPY ResNet50_label_encoder.pkl /var/task/ResNet50_label_encoder.pkl

# Set the CMD to your handler
CMD ["app.lambda_handler"]