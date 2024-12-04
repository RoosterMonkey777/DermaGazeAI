# DermaGazeAI

![Project License](https://img.shields.io/badge/license-MIT-blue)  
![Platform](https://img.shields.io/badge/platform-Android-yellow)  
![Tech Stack](https://img.shields.io/badge/tech-stack-Kotlin%20%7C%20AWS-orange)

## Overview

**DermaGazeAI** is an innovative mobile application designed to assist individuals in managing their skin health. Leveraging AI-driven skin condition analysis and comprehensive treatment management, the app aims to make dermatological care accessible and user-friendly, especially for underserved communities.

Key functionalities include:
- **AI Skin Condition Diagnosis**: Upload an image and get a preliminary diagnosis powered by machine learning.
- **Medication Management**: Search medications by **DIN (Drug Identification Number)** or name using Health Canada’s database, and manage reminders for enhanced adherence.
- **Symptom and Progress Tracking**: Log symptoms and monitor trends for proactive skin health management.
- **Dietary and Product Recommendations**: Get personalized skincare and dietary tips tailored to your condition.

---

## Features

### AI-Powered Diagnostics
- Uses a **ResNet CNN** model hosted on **AWS SageMaker**.
- Provides accurate skin condition analysis with confidence scores.

### Medication Management
- **Search medications** by DIN or name via Health Canada’s dataset.
- Add, edit, and delete personal medication records.
- Receive notifications and reminders for scheduled doses.

### Progress Tracking
- Log symptoms and view detailed analytics on treatment progress.
- Compare personal data with aggregated community trends.

### Cloud Integration
- Backend powered by **AWS DynamoDB** for secure data storage.
- **AWS Lambda** for scalable backend operations.
- Data synchronization ensures a seamless user experience across devices.

---

## Technical Architecture

### Client-Side
- **Android Native App**: Built with **Kotlin** and developed using Android Studio.
- Modular design for intuitive user interfaces, including:
  - `SearchMedicationFragment`: Search for medications using DIN or name.
  - `MedicationDetailsFragment`: View detailed medication information.
  - `UserMedicationDetailsFragment`: Add or edit personalized medication data.

### Server-Side
- **Amazon Web Services (AWS)**:
  - **DynamoDB** for secure and scalable data storage.
  - **Cognito** for user authentication and authorization.
  - **SageMaker** for hosting and managing the machine learning model.

### Workflow
1. User inputs DIN or medication name in the app.
2. Query is processed via AWS Lambda and sent to DynamoDB.
3. Data fetched from Health Canada’s database is displayed on the UI.

---

## Installation

### Prerequisites
- Android Studio (latest version)
- Kotlin 1.6+ installed
- AWS account with access to DynamoDB, Cognito, and SageMaker

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/RoosterMonkey777/DermaGazeAI.git
