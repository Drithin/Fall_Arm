/**
REST proxy for Socket server.
Shall be called by the phone application.

*/
package main

import (
	"code.google.com/p/go.net/websocket"
	"code.google.com/p/gorest"
	"encoding/json"
	"flag"
	"fmt"
	"net"
	"net/http"
	"strings"
	"sync"
)

var (
	listen     = flag.String("port", ":8080", "web server port")
	socketPort = flag.String("socketPort", "2347", "socker server port")
	socketIP   = flag.String("socketIP", "localhost", "socker server IP")
)
var _init sync.Once

func Initialize() {
	_init.Do(func() {
		flag.Parse()
	})

}

func main() {
	Initialize()

	//Regster REST services.
	gorest.RegisterService(new(RestController))
	http.Handle("/", gorest.Handle())

	err := http.ListenAndServe(*listen, nil)
	if err != nil {
		panic("Error starting RestController.")
	}

}

type RestController struct {
	gorest.RestService `root:"/controller/" consumes:"application/json" produces:"application/json"`
	register           gorest.EndPoint `method:"POST" path:"/register/" postdata:"string"`
	//version            gorest.EndPoint `method:"GET" path:"/register/"`
}

// func (serv RestController) Version() string {
// 	return "helo"
// }

func (serv RestController) Register(message string) {
	fmt.Println("RAW Message :: ", message)
	var request *Request

	err := json.Unmarshal([]byte(message), &request)
	if err == nil {
		// call proxy else ignore.
		sr := toSocketRequest(request)
		fmt.Println("Formated string : ", sr)
		//sendToSocketServer(sr)
		sendNotification(message)
	} else {
		fmt.Println("Error occured while parsing json request from phone. ", err)
	}
}
func toSocketRequest(r *Request) string {
	s := []string{r.DeviceId, r.AccX, r.AccY, r.AccZ, r.GyrX, r.GyrY, r.GyrZ, r.Datetime}
	sr := strings.Join(s, "|")
	fmt.Println("Formated string : ", sr)
	return sr
}
func sendToSocketServer(sr string) {
	_s := []string{*socketIP, *socketPort}
	host := strings.Join(_s, ":")
	conn, err := net.Dial("tcp", host)
	if err == nil {
		fmt.Fprintf(conn, sr)
	} else {
		fmt.Println("Error occured while sending request to SocketServer at "+host, err)
	}
}

type Request struct {
	DeviceId string `json:"device_id"`
	AccX     string `json:"accelerator_x"`
	AccY     string `json:"accelerator_y"`
	AccZ     string `json:"accelerator_z"`
	GyrX     string `json:"gyroscope_x"`
	GyrY     string `json:"gyroscope_y"`
	GyrZ     string `json:"gyroscope_z"`
	Datetime string `json:"datetime"`
}

func sendNotification(mess string) {
	conn, err := websocket.Dial("ws://localhost:8081/receive", "", "http://localhost")
	checkError(err)
	fmt.Println("Sending mess over websocket : " + mess)
	//err = websocket.Message.Send(conn, mess)
	err = websocket.JSON.Send(conn, mess)

	if err != nil {
		fmt.Println("Coduln't return msg ", err)
		return
	}
}
func checkError(err error) {
	fmt.Println("ERRROR IF ANY : ", err)
	if err != nil {
		fmt.Println("Fatal error ", err.Error())
		//os.Exit(1)
	}
}
