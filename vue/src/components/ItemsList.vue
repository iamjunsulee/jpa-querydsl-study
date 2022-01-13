<template>
  <v-row align="center" class="list px-3 mx-auto">
    <v-col cols="12" sm="12">
      <v-card>
        <v-card-title>Items</v-card-title>
        <v-data-table
            :headers="headers"
            :items="items"
            :loading="loading"
            disable-pagination
            :hide-default-footer="true"
        >
          <template v-slot:[`item.actions`]="{ item }">
            <v-icon small class="mx-2" @click="updateItem(item.id)">mdi-pencil</v-icon>
            <v-icon small @click="deleteItem(item.id)">mdi-delete</v-icon>
          </template>
        </v-data-table>
      </v-card>
    </v-col>
    <v-col cols="12" sm="12">
      <v-pagination
          v-model="page"
          :length="totalPages"
          circle
          next-icon="mdi-menu-right"
          prev-icon="mdi-menu-left"
          @input="pageChange"
      ></v-pagination>
    </v-col>
  </v-row>
</template>
<script>
import ItemDataService from "@/services/ItemDataService";

export default {
  name: "item-list",
  data() {
    return {
      items: [],
      headers: [
        { text: "Name", align: "start", sortable: false, value: "itemName" },
        { text: "StockQuantity", value: "stockQuantity", sortable: false },
        { text: "Price", value: "itemPrice", sortable: false },
        { text: "Actions", value: "actions", sortable: false }
      ],
      loading: false,
      page: 1,
      totalPages: 0
    };
  },
  methods: {
    findAllItems() {
      if(this.loading) return;
      this.loading = true;
      const params = this.getRequestParams(this.page);

      ItemDataService.findAllItems(params)
      .then(response => {
        const { items, totalPages } = response.data;
        this.items = items;
        this.totalPages = totalPages;
        this.loading = false;
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
        this.loading = false;
      })
    },
    refreshList() {
      this.findAllItems();
    },
    deleteItem(id) {
      ItemDataService.deleteItem(id)
      .then(() => {
        this.refreshList();
      })
      .catch(e => {
        console.log(e);
      })
    },
    updateItem(id) {
      this.$router.push({name:"item-details", params: { id: id }})
    },
    pageChange(value) {
      this.page = value;
      this.findAllItems();
    },
    getRequestParams(page) {
      let params = {};

      if (page) {
        params["page"] = page - 1;
      }
      return params;
    }
  },
  mounted() {
    this.findAllItems();
  }
};
</script>
<style>
.list {
  text-align: left;
  max-width: 750px;
  margin: auto;
}
</style>