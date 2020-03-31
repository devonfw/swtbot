pipeline { 
    agent any  
   
       stages { 
             stage('Build') { 
                steps { 
                 script {

                  tool name: 'default', type: 'org.jenkinsci.plugins.xvfb.XvfbInstallation'

               wrap([$class: 'Xvfb', additionalOptions: '', assignedLabels: '', debug: true, installationName: 'default']) {
                    timeout(50) {
                 sh 'mvn install'
                }
                 }
                    }               
            }
        
      }
    }
    }