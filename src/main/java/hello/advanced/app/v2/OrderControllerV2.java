package hello.advanced.app.v2;

import hello.advanced.app.trace.TraceStatus;
import hello.advanced.app.trace.hellotrace.HelloTraceV1;
import hello.advanced.app.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV2 {

    private final OrderServiceV2 orderService;
    private final HelloTraceV2 trace;

    @GetMapping("/v2/request")
    public String request(@RequestParam(value = "itemId") String itemId) {

        TraceStatus status = null;
        try {
            status = this.trace.begin("OrderController.request()");
            orderService.orderItem(status.getTraceId(), itemId);
            trace.end(status);
            return "ok";
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;    // 예외를 꼭 다시 던져줘야 한다.
        }
    }
}
