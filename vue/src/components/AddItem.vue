<template>
  <div class = "submit-form mt-3 mx-auto">
    <p class="headline">상품등록</p>
    <div v-if="!submitted">
      <v-form ref="form" lazy-validation>
        <v-text-field
          v-model="book.itemName"
          :rules="[v => !!v || '상품명은 필수입니다.']"
          label="상품명"
          required
          ></v-text-field>
        <v-text-field
            v-model="book.stockQuantity"
            :rules="[v => !!v || '재고량은 필수입니다.']"
            label="재고량"
            required
        ></v-text-field>
        <v-text-field
            v-model="book.itemPrice"
            :rules="[v => !!v || '상품가격은 필수입니다.']"
            label="상품가격"
            required
        ></v-text-field>
        <v-text-field
            v-model="book.author"
            :rules="[v => !!v || '작가는 필수입니다.']"
            label="작가"
            required
        ></v-text-field>
      </v-form>
      <v-btn color="success" class="mt-3" @click="saveBook">등록</v-btn>
    </div>
    <div v-else>
      <v-card class="mx-auto">
        <v-card-title>상품등록에 성공했습니다.</v-card-title>
        <v-card-subtitle>추가로 상품을 등록하고 싶다면 클릭하세요 !</v-card-subtitle>
        <v-card-actions>
          <v-btn color="primary" @click="newBook">추가</v-btn>
        </v-card-actions>
      </v-card>
    </div>
  </div>
</template>
<script>
import ItemDataService from "../services/ItemDataService"

export default {
  name: "add-book",
  data() {
    return {
      book: {
        id: null,
        itemName: "",
        itemPrice: 0,
        stockQuantity: 0,
        author: ""
      },
      submitted: false
    }
  },
  methods: {
    saveBook() {
      const validate = this.$refs.form.validate();
      if (validate) {
        ItemDataService.addBook(this.book)
            .then(response => {
              this.book.id = response.data.id;
              this.submitted = true;
            })
            .catch(e => {
              console.log(e);
            })
      }
    },
    newBook() {
      this.book = {};
      this.submitted = false;
    }
  }
};
</script>
<style>
.submit-form {
  max-width: 300px;
  margin: auto;
}
</style>