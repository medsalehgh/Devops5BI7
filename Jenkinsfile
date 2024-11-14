pipeline {
    agent any

    environment {
        SONARQUBE = 'Local SonarQube'  // Name of your SonarQube server
        SONAR_PROJECT_KEY = 'Devops5BI7'  // SonarQube project key
        SONAR_TOKEN = 'squ_0901cfdbcc1481ffd8927a9c6545107f5b672673'  // SonarQube token
        NEXUS_URL = 'http://192.168.33.10:8081'  // Nexus repository URL
        DOCKER_IMAGE_NAME = 'mrad221/tp-foyer:5.0.0'  // Docker image name
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
                        sh "mvn sonar:sonar -Dsonar.projectKey=${SONAR_PROJECT_KEY} -Dsonar.sources=src -Dsonar.login=${SONAR_TOKEN}"
                    }
                }
            }
        }

        stage('Publish Artifact to Nexus') {
            steps {
                script {
                    // Deploy the artifact to Nexus with credentials
                    withCredentials([usernamePassword(credentialsId: 'nexus-credentials', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                        sh "mvn deploy -DaltDeploymentRepository=nexus::default::http://${NEXUS_URL}/repository/maven-releases/ -Dnexus.username=${NEXUS_USER} -Dnexus.password=${NEXUS_PASS}"
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build Docker image
                    sh "docker build -t ${DOCKER_IMAGE_NAME} ."
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    // Login and push Docker image to Docker Hub
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh 'docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}'
                        sh "docker push ${DOCKER_IMAGE_NAME}"
                        sh 'docker logout'
                    }
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
