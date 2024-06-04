<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="menu">
        <h2 class="jh-entity-heading" data-cy="menuDetailsHeading"><span>Menu</span> {{ menu.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Menu Name</span>
          </dt>
          <dd>
            <span>{{ menu.menuName }}</span>
          </dd>
          <dt>
            <span>Menu Description</span>
          </dt>
          <dd>
            <span>{{ menu.menuDescription }}</span>
          </dd>
          <dt>
            <span>Icon Path</span>
          </dt>
          <dd>
            <span>{{ menu.iconPath }}</span>
          </dd>
          <dt>
            <span>Enabled</span>
          </dt>
          <dd>
            <span>{{ menu.enabled }}</span>
          </dd>
          <dt>
            <span>Dynamic Payment Menus</span>
          </dt>
          <dd>
            <span v-for="(dynamicPaymentMenus, i) in menu.dynamicPaymentMenus" :key="dynamicPaymentMenus.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'ThirdPartyIntegrationView', params: { thirdPartyIntegrationId: dynamicPaymentMenus.id } }">{{
                dynamicPaymentMenus.id
              }}</router-link>
            </span>
          </dd>
          <dt>
            <span>Parent</span>
          </dt>
          <dd>
            <div v-if="menu.parent">
              <router-link :to="{ name: 'MenuView', params: { menuId: menu.parent.id } }">{{ menu.parent.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
        <router-link v-if="menu.id" :to="{ name: 'MenuEdit', params: { menuId: menu.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span>Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./menu-details.component.ts"></script>
