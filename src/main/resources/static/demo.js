let request = require("request");

function sendGetRequestWithBody(url, body) {
    return new Promise((resolve, reject) => {
        request({
            url: url,
            method: "GET",
            json: true,
            headers: {
                "content-type": "application/json",
            },
            body
        }, function(error, response) {
            if (error != null) {
                reject(error);
            } else {
                resolve(response);
            }
        });
    });
}

let url = 'http://localhost:8888/rest/person';
sendGetRequestWithBody(url, {minAge: 20, maxAge: 40})
    .then(value => console.log(value))
    .catch(reason => console.log(reason))
