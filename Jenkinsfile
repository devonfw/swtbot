pipeline { 
    agent any  
    stages { 
        stage('Build') { 
            steps { 
			sh 'export SWT_GTK3=0'
               sh 'mvn install' 
            }
        }
    }
}