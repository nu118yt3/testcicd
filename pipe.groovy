pipeline {
    agent any

    stages {
        stage('Hello') {
            steps {
                jiraComment body: 'Message from GitHub', issueKey: 'TGJJ-1'
            }
        }
    }
}
