node {
    stage('Prepare') {
        echo 'Preparing...'
        checkout scm
    }
    stage('Build') {
        echo 'Building...'
        env.JAVA_HOME="${tool 'OpenJDK-11.0.1'}"
        env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"
        sh 'java -version'
        sh './gradlew'
    }
    stage('Report') {
        echo 'Reporting...'
        junit '**/build/test-results/test/*.xml'
        jacoco classPattern: '**/build/classes', exclusionPattern: '**/*Test*.class', execPattern: '**/build/jacoco/**.exec'
        checkstyle canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/build/reports/checkstyle/*.xml', unHealthy: ''
        pmd canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/build/reports/pmd/*.xml', unHealthy: ''
    }
    stage('Docker') {
        echo 'Building Docker Image...'
        def IMAGE_NAME = 'leanstacks/skeleton-ws-spring-boot'
        def image = docker.build("${IMAGE_NAME}:latest")
        docker.withRegistry('', 'docker-hub-leanstacks') {
        	image.push()
        }
        sh "docker image rm ${IMAGE_NAME}:latest"
    }
}