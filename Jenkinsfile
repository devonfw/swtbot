pipeline { 
    agent any  
   
       stages { 
             stage('Build') { 
                steps { 
                 script {

                 def xvfb = tool name: 'default', type: 'org.jenkinsci.plugins.xvfb.XvfbInstallation'

 

               env.PATH="${xvfb}/bin:${env.PATH}"

               wrap([$class: 'Xvfb', additionalOptions: '', assignedLabels: '', debug: true, installationName: 'default']) {
                    sh 'mvn install'
                }
}
                    }               
            }
        
      }
    }