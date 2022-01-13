import http from "../http-commons";

class ItemDataService {
    addBook(data) {
        return http.post("/api/addBook", data);
    }
    findAllItems(params) {
        return http.get("/api/items", { params });
    }
    deleteItem(id) {
        return http.delete(`/api/items/${id}`);
    }
    updateItem(id, data) {
        return http.put(`/api/items/${id}`, data);
    }
    findById(id) {
        return http.get(`/api/items/${id}`);
    }
}
export default new ItemDataService();