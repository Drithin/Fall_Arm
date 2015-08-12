// websocket implemetation for managing notfication to the nurse
// from patient.
package main

import (
	"code.google.com/p/go.net/websocket"
	"fmt"
	"net/http"
)

var (
	mychannel = make(chan string)
)

func noticeToNurseHandler(ws *websocket.Conn) {
	fmt.Println("noticeToNurseHandler activated.")
	var v string
	websocket.Message.Receive(ws, &v)
	fmt.Println(">> OPEN :: ", v)
	for {
		c := <-mychannel
		fmt.Println("Notice to Nurse Phone : ", c)
		websocket.Message.Send(ws, c)
	}
}
func patientInputHandler(ws *websocket.Conn) {
	fmt.Println("patientInputHandler activated.")
	for {
		var v string
		websocket.Message.Receive(ws, &v)
		fmt.Println("Received From server proxy : ", v)
		mychannel <- v
	}
}
func main() {

	http.Handle("/notice", websocket.Handler(noticeToNurseHandler))
	http.Handle("/receive", websocket.Handler(patientInputHandler))
	err := http.ListenAndServe(":8081", nil)
	if err != nil {
		panic("ListenAndServe: " + err.Error())
	}
}
