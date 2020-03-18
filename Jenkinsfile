pipeline { 
    agent any  
   
       stages { 
             stage('Build') { 
                steps { 
				wrap([$class: 'Xvfb', additionalOptions: '', assignedLabels: '', debug: true, installationName: 'default']) {
					sh 'mvn install'
				}
					}	           
            }
        
      }
    }
