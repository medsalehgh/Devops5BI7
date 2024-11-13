pipeline {
    agent any

    environment {
        SONARQUBE = 'Local SonarQube'  // Name of your SonarQube server
        SONAR_PROJECT_KEY = 'Devops5BI7'  // SonarQube project key
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
    }
}
