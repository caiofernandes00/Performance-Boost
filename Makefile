export GRAALVM_HOME ~= "C:\\Users\\caiow\\apps\\graalvm-jdk-17.0.9+11.1"

########### CACHE APP ###########
caching_app_build_jar:
	gradlew.bat :caching-app:quarkusBuild

caching_app_build_native:
	gradlew.bat :caching-app:build -Dquarkus.package.type=native -Dquarkus.native.container-build=true

caching_app_run_dev:
	gradlew.bat :caching-app:quarkusDev

caching_app_run_jar: caching_app_build_jar
	java -jar ./caching-app/build/quarkus-app/quarkus-run.jar

caching_app_run_native: caching_app_build_native
	./caching-app/build/quarkus-app/caching-app-runner

caching_app_run_perf_test:
	locust -f ./perf-test/caching-app.py --users 100000 --spawn-rate 100 --run-time 10m -H http://localhost:8080

caching_app_with_etag_run_perf_test:
	locust -f ./perf-test/caching-app-with-etag.py --users 100000 --spawn-rate 100 --run-time 10m -H http://localhost:8080


.PHONY: caching_app_build_jar caching_app_build_native caching_app_run_dev caching_app_run_jar caching_app_run_native caching_app_run_perf_test caching_app_with_etag_run_perf_test