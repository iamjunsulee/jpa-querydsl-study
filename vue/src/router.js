import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

export default new Router({
    mode: "history",
    routes: [
        {
            path: "/members",
            name: "members",
            component: () => import("./components/MembersList")
        },
        {
            path: "/join",
            name: "join",
            component: () => import("./components/MemberJoin")
        },
        {
            path: "/addItem",
            name: "addItem",
            component: () => import("./components/AddItem")
        },
        {
            path: "/items",
            name: "items",
            component: () => import("./components/ItemsList")
        },
        {
            path: "/modifyMember",
            name: "member-details",
            component: () => import("./components/Member")
        },
        {
            path: "/modifyItem",
            name: "item-details",
            component: () => import("./components/Item")
        },
        {
            path: "/order",
            name: "order",
            component: () => import("./components/Order")
        },
        {
            path: "/orders",
            name: "orders",
            component: () => import("./components/OrderList")
        }
    ]
});