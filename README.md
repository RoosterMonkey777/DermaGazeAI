
# DermaGazeAI

![Project License](https://img.shields.io/badge/license-MIT-blue)  
![Platform](https://img.shields.io/badge/platform-Android-yellow)  
![Tech Stack](https://img.shields.io/badge/tech-stack-Kotlin%20%7C%20AWS%20%7C%20ML-orange)

---

## Overview

**DermaGazeAI** is an innovative mobile application designed to revolutionize skin health management. By leveraging cutting-edge AI technology, cloud computing, and an intuitive mobile interface, the app empowers users with tools for skin condition diagnosis, medication management, and personalized skincare recommendations. 

The app was developed as a capstone project to address the challenges of limited dermatological access, self-diagnosis risks, and medication adherence, particularly in underserved regions.

---

## Features

### **1. AI Skin Condition Diagnosis**
- **How it works**: Users upload images of their skin lesions, and the app uses a **ResNet CNN model** hosted on AWS SageMaker to provide a preliminary diagnosis.
- **Skin conditions covered**: Eczema, acne, and other common conditions.
- **Benefits**: Early detection and improved access to dermatological insights.

### **2. Medication Management**
- **Search functionality**: 
  - Search by **Drug Identification Number (DIN)** or medication name.
  - Integrated with Health Canada’s medication database.
- **User tools**: Add, edit, delete, and set reminders for medications.
- **Notifications**: Receive cloud-based reminders to enhance adherence to treatment.

### **3. Skincare Recommendations**
- **Personalized product suggestions**: Based on skin type, condition, and user preferences.
- **Natural Language Processing (NLP)**: Uses **TF-IDF vectorization** and **cosine similarity** to match user needs with product features.

### **4. Progress Tracking**
- **Health monitoring**: Users can log symptoms and view analytics to track treatment effectiveness.
- **Data insights**: Compare individual progress with community trends for motivational benchmarking.

### **5. Data Privacy & Security**
- Adherence to strict data protection regulations.
- AWS Cognito ensures secure user authentication and encrypted data storage.

---

## Technical Architecture

### **Client-Side**
- **Platform**: Android native application.
- **Programming Language**: Kotlin.
- **Development Environment**: Android Studio IDE.
- **UI Features**:
  - Medication search and management.
  - Symptom logging and analytics display.
  - Skin condition diagnosis interface.

### **Server-Side**
- **Cloud Provider**: Amazon Web Services (AWS).
- **Services Used**:
  - **AWS Amplify**: Backend-as-a-Service for seamless integration.
  - **AWS Lambda**: Serverless backend for efficient processing.
  - **AWS DynamoDB**: NoSQL database for user and app data.
  - **AWS SageMaker**: Hosts and manages the AI model.
  - **AWS S3**: Image storage for user-uploaded photos.

### **Machine Learning**
- **Computer Vision**:
  - Model: ResNet50 CNN architecture.
  - Hosted on AWS SageMaker and integrated via API endpoints.
  - Deployed using Docker and AWS Elastic Container Registry (ECR).
- **NLP for Recommendations**:
  - Technique: TF-IDF vectorization.
  - Matches product features with user preferences using cosine similarity.

---

## Installation

### **Prerequisites**
- Android Studio (latest version)
- Kotlin 1.6+ installed
- AWS account with access to Cognito, DynamoDB, SageMaker, and other relevant services.

### **Steps**
1. Clone the repository:
   ```bash
   git clone https://github.com/RoosterMonkey777/DermaGazeAI.git
   ```
2. Open the project in Android Studio.
3. Configure AWS credentials in the `Amplify` and `SageMaker` setup files.
4. Build and run the app on an Android emulator or device.

---

## Project Tools

### **JIRA**
- [Main Board](https://cremacle.atlassian.net/jira/software/projects/DPP/boards/1)
- [Risk Board](https://cremacle.atlassian.net/jira/software/projects/DRM/boards/2)
- [Test Board](https://cremacle.atlassian.net/jira/software/projects/DTP/boards/3)

### **GitHub Repositories**
- [Main Project Repo](https://github.com/RoosterMonkey777/DermaGazeAI/tree/test-2git-)
- [Machine Learning Repo](https://github.com/RoosterMonkey777/DermaGazeAi-ML.git)

### **Other Tools**
- [MS Teams](https://teams.microsoft.com/v2/?culture=en-ca&country=ca)
- [VPository](https://online.visual-paradigm.com/admin.jsp#projects)
- [Mindomo Mindmap](https://www.mindomo.com/mindmap/42fd2b0390894a0fb86326817779ead7?b=639)

---

## Future Enhancements

- Expand skin condition classification to include more conditions.
- Introduce real-time video diagnosis for better accuracy.
- Develop a cross-platform version for iOS.
- Add multilingual support to reach a global audience.
- Explore partnerships with dermatology clinics and healthcare providers.

---

## Contribution Guidelines

We welcome contributions! To get started:
1. Fork the repository.
2. Create a new branch (`feature/your-feature-name`).
3. Commit your changes and push to your branch.
4. Submit a pull request for review.

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## Contact

- **Author**: Zahaak Khan and Bayan Ahmadi 
- 
- **GitHub**: [Bayan Ahmadi](https://github.com/RoosterMonkey777)  

For inquiries, feel free to reach out or explore the [GitHub project repository](https://github.com/RoosterMonkey777/DermaGazeAI).
