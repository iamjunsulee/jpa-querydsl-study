import http from "../http-commons";

class MemberDataService {
    getAll() {
        return http.get("/api/v2/members");
    }
    join(data) {
        return http.post("/api/v2/members", data);
    }
    findById(id) {
        return http.get(`/api/members/${id}`);
    }
    deleteMember(id) {
        return http.delete(`/api/members/${id}`);
    }
    updateMember(id, data) {
        return http.put(`/api/members/${id}`, data);
    }
    findMembersWithPage(params) {
        return http.get("/api/members", { params });
    }
}
export default new MemberDataService();