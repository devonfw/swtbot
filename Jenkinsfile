pipeline { 
    agent any  
    stages { 
        stage('Build') { 
            steps { 
			sh './eclipse -noSplash --launcher.GTK_Check=0'
               sh 'mvn install' 
            }
        }
    }
}