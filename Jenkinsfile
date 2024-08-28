pipeline {
    agent any

    tools {
        // Specify Maven version here if required
        maven 'Maven 3.6.3'
    }

    environment {
        // Define environment variables if needed
        MAVEN_HOME = tool 'Maven 3.6.3'
    }

    stages {
        stage('Checkout') {
            steps {
                // Check out the code from your source control
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Clean and build the project with Maven
                sh "'${MAVEN_HOME}/bin/mvn' clean install"
            }
        }

        stage('Test') {
            steps {
                // Run tests with Maven
                sh "'${MAVEN_HOME}/bin/mvn' test"
            }
        }

        stage('Archive Test Results') {
            steps {
                // Archive test results
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Deploy') {
            when {
                // Optional: deploy only on the master branch or specific condition
                branch 'main'
            }
            steps {
                // Deploy your application
                // Example: sh "'${MAVEN_HOME}/bin/mvn' deploy"
            }
        }
    }

    post {
        success {
            echo 'Pipeline succeeded!'
        }
        failure {
            echo 'Pipeline failed!'
        }
        always {
            echo 'Pipeline finished.'
        }
    }
}
