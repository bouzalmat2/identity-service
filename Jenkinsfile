pipeline {
    agent any

    environment {

        DOCKER_HUB_USER = "bouzalmat"
        IMAGE_NAME = "smartmaintain-identity-service"
        DOCKER_HUB_CREDS = "docker-hub"
    }

    stages {
        stage('Checkout') {
            steps {

                checkout scm
            }
        }

        stage('Maven Build') {
            steps {

                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build & Push Docker Image') {
            steps {
                script {

                    docker.withRegistry('', "${DOCKER_HUB_CREDS}") {
                        def identityImage = docker.build("${DOCKER_HUB_USER}/${IMAGE_NAME}")
                        identityImage.push('latest')
                        identityImage.push("${env.BUILD_ID}")
                    }
                }
            }
        }

        stage('Ansible Deploy') {
            steps {
                // Jenkins k-i-3yet l-Ansible bach i-7et l-container f l-server
                // T-akkdi belli 3ndek dossier 'ansible' fih l-playbook
                ansiblePlaybook(
                    playbook: 'ansible/deploy.yml',
                    inventory: 'ansible/inventory.ini'
                )
            }
        }
    }

    post {
        success {
            echo "✅ Pipeline daza mzyan o l-image t-poushat l-Docker Hub!"
        }
        failure {
            echo "❌ Kayn chi mouchkil f l-build, checki l-logs."
        }
    }
}