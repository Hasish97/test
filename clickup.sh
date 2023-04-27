set -x
java -jar /var/lib/jenkins/workspace/test/clickup/target/ClickupDashboard-0.0.1-SNAPSHOT.jar > clickup.log &
set +x