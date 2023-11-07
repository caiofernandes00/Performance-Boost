export GRAALVM_HOME ~= "C:\\Users\\caiow\\apps\\graalvm-jdk-17.0.9+11.1"

########### CACHE APP ###########
cache_performance_build_jar:
	gradlew.bat :cache-performance:quarkusBuild

cache_performance_build_native:
	gradlew.bat :cache-performance:build -Dquarkus.package.type=native -Dquarkus.native.container-build=true

cache_performance_run_dev:
	gradlew.bat :cache-performance:quarkusDev

cache_performance_run_jar: cache_performance_build_jar
	java -jar ./cache-performance/build/quarkus-app/quarkus-run.jar

cache_performance_run_native: cache_performance_build_native
	./cache-performance/build/quarkus-app/cache-performance-runner

cache_performance_run_perf_test:
	locust -f ./perf-test/cache-performance.py --headless --users 100000 --spawn-rate 100 --run-time 10m -H http://localhost:8080


.PHONY: cache_performance_build_jar cache_performance_build_native cache_performance_run_dev cache_performance_run_jar cache_performance_run_native cache_performance_run_perf_test