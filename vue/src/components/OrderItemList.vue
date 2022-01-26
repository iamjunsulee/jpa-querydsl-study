<template>
  <v-row align="center" class="orderItem_list px-3 mx-auto">
    <v-col cols="12" sm="12">
      <v-card>
        <v-card-title>OrderItems</v-card-title>
        <v-data-table
            :headers="headers"
            :items="orderItems"
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
            <v-icon small class="mx-2" @click="getOrderItemsDetail(item.orderId)">mdi-pencil</v-icon>
            <v-icon v-if="item.orderStatus === 'ORDERED'" color="red" small @click="cancelOrder(item.orderId)">mdi-close-outline</v-icon>
          </template>
        </v-data-table>
      </v-card>
    </v-col>
  </v-row>
</template>
<script>
import OrderItemDataService from "@/services/OrderItemDataService";

export default {
  name: "orderItem-list",
  data() {
    return {
      orderItems: [],
      headers: [
        { text: "상품명", value: "itemName", sortable: false },
        { text: "주문수량", align: "center", value: "orderQuantity", sortable: false },
        { text: "주문가격", align: "center", value: "orderPrice", sortable: false }
      ],
      loading: false
    }
  },
  methods: {
    getAllOrderItems(id) {
      if(this.loading) return;
      this.loading = true;
      OrderItemDataService.getAllOrderItems(id)
      .then(response => {
        this.orderItems = response.data.data;
        console.log(response.data.data);
        this.loading = false;
      })
      .catch(e => {
        console.log(e);
        this.loading = false;
      })
    }
  },
  mounted() {
    this.getAllOrderItems(this.$route.params.orderId);
  }
}
</script>
<style>
.orderItem_list {
  text-align: left;
  max-width: 600px;
  margin: auto;
}
</style>