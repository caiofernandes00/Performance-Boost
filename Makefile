export GRAALVM_HOME ~= "C:\\Users\\caiow\\apps\\graalvm-jdk-17.0.9+11.1"

########### CACHE APP ###########
cache_performance_run:
	gradlew.bat :cache-performance:quarkusDev

########### CACHE APP ###########
perf_run:
	locust -f ./perf-test/cache-performance.py --headless --users 1000 --spawn-rate 1 --run-time 10m -H http://localhost:8080


.PHONY: cache_performance_run