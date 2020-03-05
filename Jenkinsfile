pipeline { 
    agent any  
    stages { 
        stage('Build') { 
            steps { 
			   sh Xvfb :1 -ac -screen 0 1024x768x8 &
               sh export DISPLAY=:1
               sh 'mvn install' 
            }
        }
    }
}