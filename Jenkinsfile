pipeline {
    agent any

    stages {
        stage('Hello') {
            steps {
                jiraComment body: 'Message from GitHub', issueKey: 'TI-1'
            }
        }
    }
}
