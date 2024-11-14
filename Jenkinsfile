pipeline {
    agent any

    environment {
        SONARQUBE = 'Local SonarQube'  // Name of your SonarQube server
        SONAR_PROJECT_KEY = 'Devops5BI7'  // SonarQube project key
        SONAR_TOKEN = 'squ_0901cfdbcc1481ffd8927a9c6545107f5b672673'  // SonarQube token
        NEXUS_URL = 'http://192.168.33.10:8081'  // Nexus repository URL
        NEXUS_CREDENTIALS = 'admin:admin'  // Nexus credentials
        DOCKER_IMAGE_NAME = 'mrad221/tp-foyer:5.0.0'  // Docker image name
        DOCKER_REGISTRY = 'docker.io'  // Docker Hub registry
        DOCKER_CREDS = 'docker-hub-credentials'  // Docker Hub credentials ID (to be created in Jenkins)
    }

    stages {
        stage('Checkout') {
            steps {
                // Clone the repository from GitHub
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                script {
                    // Run your Maven build and tests (JUnit & Mockito)
                    sh 'mvn clean test'  // Runs the Maven clean and test phases
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    // Run SonarQube analysis
                    withSonarQubeEnv('Local SonarQube') {
                        sh 'mvn sonar:sonar -Dsonar.projectKey=${SONAR_PROJECT_KEY} -Dsonar.sources=src'
                    }
                }
            }
        }

        stage('Publish Artifact to Nexus') {
            steps {
                script {
                    // Deploy the artifact to Nexus
                    sh 'mvn deploy -DaltDeploymentRepository=nexus::default::http://${NEXUS_URL}/repository/maven-releases/'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build Docker image
                    sh 'docker build -t ${DOCKER_IMAGE_NAME} .'
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    // Login to Docker Hub
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh 'docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}'
                    }
                    // Push Docker image to Docker Hub
                    sh 'docker push ${DOCKER_IMAGE_NAME}'
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                script {
                    // Run docker-compose to start services
                    sh 'docker-compose up -d'
                }
            }
        }
    }
}
