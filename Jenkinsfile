pipeline { 
    agent any  
    stages { 
        stage('Build') { 
            steps { 
               sh 'mvn clean install' 
            }
        }
    }
	 post {
        always {
            deleteDir()
        }
    }
}