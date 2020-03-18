pipeline { 
    agent any  
    stages { 
        stage('Build') { 
            steps { 
			
			wrap([$class:'Xvnc']) { // takeScreenshot: true, causes issues seemingly
										sh 'export SWT_GTK3=0' // disable GTK3 as of linux bug (see also https://bbs.archlinux.org/viewtopic.php?id=218587)
									sh 'mvn install'
									}
			
			
              
            }
        }
    }
}