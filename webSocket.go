// websocket implemetation for managing notfication to the nurse
// from patient.
package main

import (
	"code.google.com/p/go.net/websocket"
	"net/http"
)

var (
	channel = make(chan string)
)

func noticeToNurseHandler(ws *websocket.Conn) {
	for {
		var c = <-channel
		websocket.Message.Send(ws, c)
	}
	//ws.Write("Helo")
}
func patientInputHandler(ws *websocket.Conn) {
	for {
		v := ""
		websocket.Message.Receive(ws, v)
		channel <- v
	}
	//ws.Write("Helo")
}
func main() {

	http.Handle("/notice", websocket.Handler(noticeToNurseHandler))
	http.Handle("/receive", websocket.Handler(patientInputHandler))
	err := http.ListenAndServe(":9999", nil)
	if err != nil {
		panic("ListenAndServe: " + err.Error())
	}
}
