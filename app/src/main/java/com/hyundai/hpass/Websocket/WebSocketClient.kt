package com.hyundai.hpass.Websocket

/**
 *
 * @author 김은서
 *
 */
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener

enum class WebSocketConnectionState {
    CONNECTED,
    DISCONNECTED
}

class WebSocketClient(url: String) {
    private val client: OkHttpClient = OkHttpClient()
    private val request: Request = Request.Builder().url(url).build()
    private var webSocket: WebSocket? = null
    private val listener: EchoWebSocketListener = EchoWebSocketListener()
    var connectionState: WebSocketConnectionState = WebSocketConnectionState.DISCONNECTED
    fun start() {
        client.newWebSocket(request, listener) // 소켓 시작
    }

    fun sendMessage(message: String) { //메시지 보내기
        webSocket?.send(message) ?: println("WebSocket is not connected.")
    }

    private inner class EchoWebSocketListener :WebSocketListener(){
        override fun onMessage(webSocket: WebSocket, text: String) {
            println("Message From Server: $text")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
            println("Error: " + t.message)
        }
        override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
            Log.d("socket", "open, $response")
            this@WebSocketClient.webSocket = webSocket // 연결된 웹소켓 객체 저장
            connectionState = WebSocketConnectionState.CONNECTED
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            webSocket.close(1000, null)
            connectionState = WebSocketConnectionState.DISCONNECTED
            println("socket Close: $code / $reason")
        }
    }
}
