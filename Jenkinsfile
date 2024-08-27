pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/AjayVish1/Ajay-AutomationTestScript.git', branch: 'main'
            }
        }
        stage('Build') {
            steps {
                echo 'Building with Maven...'
                bat 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                echo 'Running tests...'
                bat 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying...'
                // Deployment commands go here
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
