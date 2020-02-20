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
            cleanWs(patterns: [[pattern: '/home/pl/SWTBOT-repo/', type: 'INCLUDE']])
        }
    }
}