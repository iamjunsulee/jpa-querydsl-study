<template>
  <div class = "submit-form mt-3 mx-auto">
    <p class="headline">상품주문</p>
    <v-form ref="form" lazy-validation>
      <v-combobox
          dense
          solo
          :items="members"
          item-text="name"
          item-value="id"
          label="회원을 선택하세요"
          v-model="selectedMember"
          @change="setMemberInfo"
      >
      </v-combobox>
      <p>배송지</p>
      <v-text-field
          v-model="orderRequest.address.city"
          :rules="[v => !!v || '주소는 필수입니다.']"
          label="주소1"
          required
      ></v-text-field>
      <v-text-field
          v-model="orderRequest.address.street"
          :rules="[v => !!v || '주소는 필수입니다.']"
          label="주소2"
          required
      ></v-text-field>
      <v-text-field
          v-model="orderRequest.address.zipcode"
          :rules="[v => !!v || '우편번호은 필수입니다.']"
          label="우편번호"
          required
      ></v-text-field>
      <v-combobox
          dense
          solo
          :items="items"
          item-text="itemName"
          item-value="id"
          label="상품을 선택하세요"
          v-model="selectedItem"
          @change="setItemInfo"
      >
      </v-combobox>
      <v-text-field
        v-model.number="orderRequest.orderQuantity"
        :rules="[v => !!v || '주문수량은 필수입니다.', v => (v > 0) || '주문수량은 0보다 커야합니다.']"
        label="주문수량"
        required
      >
        <v-icon
            slot="append-outer"
            color="blue"
            @click="increment"
        >
          mdi-plus
        </v-icon>
        <v-icon
            slot="prepend"
            color="red"
            @click="decrement"
        >
          mdi-minus
        </v-icon>
      </v-text-field>
      <v-text-field
          v-model="orderRequest.orderPrice"
          :rules="[v => !!v || '주문가격은 필수입니다.']"
          label="주문가격"
          required
          disabled
      ></v-text-field>
    </v-form>
    <v-btn color="primary" class="mt-3" @click="createOrder">주문</v-btn>
  </div>
</template>

<script>
import ItemDataService from "@/services/ItemDataService";
import MemberDataService from "@/services/MemberDataService";
import OrderDataService from "@/services/OrderDataService";

export default {
  name: "order",
  data() {
    return {
      selectedItem: null,
      selectedMember: null,
      items: [],
      members: [],
      orderRequest: {
        memberId: "",
        itemId: "",
        orderQuantity: 0,
        orderPrice: 0,
        address: {
          city: "",
          street: "",
          zipcode: ""
        }
      },
      order: {
        id: null
      }
    }
  },
  methods: {
    getAllItems() {
      ItemDataService.findAllItems()
      .then(response => {
        this.items = response.data.data;
        console.log(response.data.data);
      })
      .catch(e => {
        console.log(e);
      })
    },
    getAllMembers() {
      MemberDataService.getAll()
      .then(response => {
        this.members = response.data.data;
        console.log(response.data.data);
      })
      .catch(e => {
        console.log(e);
      })
    },
    setItemInfo() {
      this.orderRequest.orderPrice = this.selectedItem.itemPrice;
      this.orderRequest.itemId = this.selectedItem.id;
      this.orderRequest.orderQuantity = 0;
    },
    setMemberInfo() {
      this.orderRequest.memberId = this.selectedMember.id;
      this.orderRequest.address.city = this.selectedMember.address.city;
      this.orderRequest.address.street = this.selectedMember.address.street;
      this.orderRequest.address.zipcode = this.selectedMember.address.zipcode;
    },
    increment() {
      this.orderRequest.orderQuantity = parseInt(this.orderRequest.orderQuantity, 10) + 1;
    },
    decrement() {
      this.orderRequest.orderQuantity = parseInt(this.orderRequest.orderQuantity, 10) - 1;
    },
    createOrder() {
      const validate = this.$refs.form.validate();
      if (validate) {
        OrderDataService.createOrder(this.orderRequest)
            .then(response => {
              this.order.id = response.data.id;
              console.log(response.data);
              this.$router.push({name: "orders"});
            })
            .catch(e => {
              console.log(e);
            })
      }
    }
  },
  mounted() {
    this.getAllItems();
    this.getAllMembers();
  }
}
</script>

<style>
.submit-form {
  max-width: 300px;
  margin: auto;
}
</style>