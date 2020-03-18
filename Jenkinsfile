pipeline { 
    agent any  
   
       stages { 
           wrap([$class: 'Xvfb', additionalOptions: '', assignedLabels: '', debug: true, installationName: 'default']) {
             stage('Build') { 
                steps { 
					sh 'mvn install'
				}
						           
            }
        }
      }
    }
