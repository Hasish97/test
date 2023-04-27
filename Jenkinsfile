pipeline {
    agent any
    options {
        skipStagesAfterUnstable()
        disableConcurrentBuilds()
    }

    stages {
        stage('Stop app running on port 8080') {
        
            steps {
                sh 'sudo lsof -t -i:8080 | xargs --no-run-if-empty sudo kill -9'
            }
        }

        stage('git repo & clean') {
            steps {
               //sh "rm -rf  /s /q clickup"
                //sh "git clone https://github.com/Hasish97/clickup.git"
                sh "mvn clean"
            }
        }
        stage('install & build') {
            steps {
                sh "mvn clean install"
            }
        }
        stage('test') {
            steps {
                sh "mvn test"
            }
        }
        stage('package') {
            steps {
                sh "mvn package"
            }
        }
        
        stage('Deploy and Run') {
                steps {
                    sh 'sudo systemctl start clickup'
                }
            }
            
            //steps {
              
                //sh "bash ./clickup.sh"
                
            //}
        
        

    }

    
}
