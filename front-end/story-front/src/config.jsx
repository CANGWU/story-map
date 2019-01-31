const host = "http://cangwu.art"
const port = 9000
const baseURL = host + ':' + port

const API = {
    query: (url, opt) => {
        let jwt = {}
        try {
            jwt = JSON.parse(localStorage.getItem('auth')) || {}
        } catch (e) { console.error(e) }
        return fetch(url, {
            method: 'GET',
            headers:{
                'Authorization': jwt.token,
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            ...(opt || {})
        }).then((res) => res.json())
    }
}

export {baseURL, API}