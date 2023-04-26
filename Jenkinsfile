pipeline {
    agent any

    stages {
        stage('Stop app running on port 8080') {
            steps {
                sh 'sudo lsof -t -i:8080 | xargs --no-run-if-empty sudo kill -9'
            }
        }

        stage('git repo & clean') {
            steps {
               sh "rm -rf  /s /q clickup"
                sh "git clone https://github.com/Hasish97/clickup.git"
                sh "mvn clean -f clickup"
            }
        }
        stage('install & build') {
            steps {
                sh "mvn clean install -f clickup"
            }
        }
        stage('test') {
            steps {
                sh "mvn test -f clickup"
            }
        }
        stage('package') {
            steps {
                sh "mvn package -f clickup"
            }
        }
        //stage('Deploy and Run') {
            //steps {
                //withEnv(['JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64']) {
                //sh 'cd /var/lib/jenkins/workspace/clickupp/clickup && sudo -u jenkins ./startclickup.sh'
                //sh '/var/lib/jenkins/workspace/test/clickup/startclickup.sh'

                //}
            //}
        //}
        //stage('Deploy and Run') {
            //steps {
              
                //sh "/var/lib/jenkins/workspace/clickupp/clickup/target/startclickup.sh"
                
            //}
        
        //}

    }

    post {
        always {
            sh "nohup java -jar /var/lib/jenkins/workspace/test/clickup/target/ClickupDashboard-0.0.1-SNAPSHOT.jar &"
            sh "sleep 20"
        }
    }
}
