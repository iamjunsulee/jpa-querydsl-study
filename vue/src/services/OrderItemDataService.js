import http from "../http-commons"

class OrderItemDataService {
    getAllOrderItems(id) {
        return http.get(`/api/orders/${id}/orderItems`)
    }
}
export default new OrderItemDataService();