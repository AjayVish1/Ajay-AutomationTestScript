pipeline {
    agent any

//     tools {
//         // Use the exact name configured in Jenkins
//         maven 'Maven 3.6.3'
//     }

    environment {
        // Set the Maven home environment variable
        MAVEN_HOME = tool name: 'Maven 3.6.3', type: 'maven'
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout code from the repository
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Build the project using Maven
                sh "'${MAVEN_HOME}/bin/mvn' clean install"
            }
        }

        stage('Test') {
            steps {
                // Run tests using Maven
                sh "'${MAVEN_HOME}/bin/mvn' test"
            }
        }

        stage('Archive Test Results') {
            steps {
                // Archive test results from the Maven surefire reports
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Deploy') {
            when {
                branch 'main'  // Only deploy from the 'main' branch
            }
            steps {
                // Deploy the application
                sh "'${MAVEN_HOME}/bin/mvn' deploy"
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
