<template>
  <v-row align="center" class="order_list px-3 mx-auto">
    <v-col cols="12" sm="12">
      <v-card>
        <v-card-title>Orders</v-card-title>
        <v-data-table
          :headers="headers"
          :items="orders"
          :loading="loading"
          disable-pagination
          :hide-default-footer="true"
          >
          <template v-slot:item.orderStatus="{ item }">
            <v-chip
                :color="getColor(item.orderStatus)"
            >
              {{ item.orderStatus }}
            </v-chip>
          </template>
          <template v-slot:item.cancel="{ item }">
            <v-icon v-if="item.orderStatus === 'ORDERED'" color="red" small @click="cancelOrder(item.orderId)">mdi-close-outline</v-icon>
          </template>
        </v-data-table>
      </v-card>
    </v-col>
  </v-row>
</template>
<script>
import OrderDataService from "@/services/OrderDataService";

export default {
  name: "order_list",
  data() {
    return {
      orders: [],
      headers: [
        { text: "주문자명", align: "start", sortable: false, value: "name" },
        { text: "배송주소1", value: "address.city", sortable: false },
        { text: "배송주소2", value: "address.street", sortable: false },
        { text: "우편번호", align: "center", value: "address.zipcode", sortable: false },
        { text: "상품명", value: "orderItems[0].itemName", sortable: false },
        { text: "주문수량", align: "center", value: "orderItems[0].orderQuantity", sortable: false },
        { text: "주문일자", value: "orderDate", sortable: false },
        { text: "주문상태", align: "center", value: "orderStatus", sortable: false },
        { text: "주문취소", align: "center", value: "cancel", sortable: false}
      ],
      loading: false
    }
  },
  methods: {
    getAllOrders() {
      if(this.loading) return;
      this.loading = true;
      OrderDataService.getAllOrders()
      .then(response => {
        this.orders = response.data;
        console.log(response.data);
        this.loading = false;
      })
      .catch(e => {
        console.log(e);
        this.loading = false;
      })
    },
    refreshList() {
      this.getAllOrders();
    },
    cancelOrder(id) {
      OrderDataService.cancelOrder(id)
      .then(() => {
        this.refreshList();
      })
      .catch(e => {
        console.log(e);
      })
    },
    getColor(orderStatus) {
      if (orderStatus === 'CANCELED') return 'red';
      else return 'blue';
    }
  },
  mounted() {
    this.getAllOrders();
  }
}
</script>
<style>
.order_list {
  text-align: left;
  max-width: 1150px;
  margin: auto;
}
</style>