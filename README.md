# weather-updates

## Setup

1. get Current Weather Data API key from https://openweathermap.org/api
1. create file `src/main/resources/api.properties`
1. insert API key in the form `API_KEY = {your API key}`

## Endpoints

### POST /weather-updates/add-location
Adds valid locations to list to get weather updates every 15 minutes. 
#### RequestBody
```javascript
{
  "location": string
}
```
#### Response
`string`

### GET /weather-updates/get-info
Displays all saved updates for location.  
#### RequestBody
```javascript
{
  "location": string
}
```
#### Response
```javascript
[
  {
    "name": string,
    "temperature": number,
    "windSpeed": number,
    "humidity": number,
    "time": string
  }
]
```

### GET /weather-updates/display-list
Displays currently active locations. 
#### Response
```javascript
string[]
```

### DELETE /weather-updates/delete-location
Deletes location and its saved updates.
#### RequestBody
```javascript
{
  "location": string
}
```
#### Response
`string`
