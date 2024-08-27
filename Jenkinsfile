pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from GitHub
                git url: 'https://github.com/AjayVish1/Ajay-AutomationTestScript.git', branch: 'main'
            }
        }
        stage('Build') {
            steps {
                // Build the project using Maven
                echo 'Building with Maven...'
                sh 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                // Run tests using Maven
                echo 'Running tests...'
                sh 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
                // Optional: Add deployment steps here if needed
                echo 'Deploying...'
                // Example command: sh 'mvn deploy'
            }
        }
    }

    post {
        success {
            echo 'Pipeline succeeded!'
        }
        failure {
            echo 'Pipeline failed.'
        }
        always {
            echo 'This will always run.'
        }
    }
}
