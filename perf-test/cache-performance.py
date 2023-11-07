from locust import HttpUser, task, between, constant

class MyUser(HttpUser):
    wait_time = between(1, 5)

    @task
    def post_request(self):
        headers = {
            "Content-Type": "application/json"
        }
        payload = {
            "name": "fake-name"
        }

        response = self.client.post("/orders", json=payload, headers=headers)

    @task
    def get_all(self):
        params = {"page": 0, "perPage": 10}
        self.client.get("/orders", params=params)
