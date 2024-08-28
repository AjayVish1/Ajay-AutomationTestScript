pipeline {
    agent any

    tools {
        maven 'Maven 3.9.6'  // Define the Maven version here, ensure it's configured in Jenkins Global Tool Configuration
    }

    parameters {
        string(name: 'BRANCH', defaultValue: 'main', description: 'Branch to build')
        string(name: 'ENVIRONMENT', defaultValue: 'dev', description: 'Deployment environment')
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/AjayVish1/Ajay-AutomationTestScript.git', branch: "${params.BRANCH}"
            }
        }
        stage('Build') {
            steps {
                echo 'Building with Maven...'
                bat 'mvn clean install'
                archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true  // Archive build artifacts
            }
        }
        stage('Test') {
            steps {
                echo 'Running tests...'
                bat 'mvn test'
                junit '**/target/test-classes/TEST-*.xml'  // Archive test results
            }
        }
        stage('Deploy') {
            steps {
                echo "Deploying to ${params.ENVIRONMENT} environment..."
                // Deployment commands go here
                // Example: bat 'deploy.bat'
            }
        }
    }

    post {
        success {
            echo 'Pipeline succeeded!'
            // Add notification steps here if needed
            // Example: mail to: 'team@example.com', subject: 'Build Success', body: 'The build was successful.'
        }
        failure {
            echo 'Pipeline failed.'
            // Add notification steps here if needed
            // Example: mail to: 'team@example.com', subject: 'Build Failure', body: 'The build failed.'
        }
        always {
            echo 'This will always run.'
            // Clean up or additional steps go here
        }
    }
}
