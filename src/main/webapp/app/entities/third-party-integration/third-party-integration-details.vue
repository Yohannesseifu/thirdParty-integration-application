<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="thirdPartyIntegration">
        <h2 class="jh-entity-heading" data-cy="thirdPartyIntegrationDetailsHeading">
          <span>Third Party Integration</span> {{ thirdPartyIntegration.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Is Draft</span>
          </dt>
          <dd>
            <span>{{ thirdPartyIntegration.isDraft }}</span>
          </dd>
          <dt>
            <span>Integration Name</span>
          </dt>
          <dd>
            <span>{{ thirdPartyIntegration.integrationName }}</span>
          </dd>
          <dt>
            <span>Company Name</span>
          </dt>
          <dd>
            <span>{{ thirdPartyIntegration.companyName }}</span>
          </dd>
          <dt>
            <span>Description</span>
          </dt>
          <dd>
            <span>{{ thirdPartyIntegration.description }}</span>
          </dd>
          <dt>
            <span>Icon Path</span>
          </dt>
          <dd>
            <span>{{ thirdPartyIntegration.iconPath }}</span>
          </dd>
          <dt>
            <span>Enabled</span>
          </dt>
          <dd>
            <span>{{ thirdPartyIntegration.enabled }}</span>
          </dd>
          <dt>
            <span>Account Number</span>
          </dt>
          <dd>
            <span>{{ thirdPartyIntegration.accountNumber }}</span>
          </dd>
          <dt>
            <span>Minimum Amount</span>
          </dt>
          <dd>
            <span>{{ thirdPartyIntegration.minimumAmount }}</span>
          </dd>
          <dt>
            <span>Maximum Amount</span>
          </dt>
          <dd>
            <span>{{ thirdPartyIntegration.maximumAmount }}</span>
          </dd>
          <dt>
            <span>Currency Code</span>
          </dt>
          <dd>
            <span>{{ thirdPartyIntegration.currencyCode }}</span>
          </dd>
          <dt>
            <span>Payment Confirmation Template</span>
          </dt>
          <dd>
            <span>{{ thirdPartyIntegration.paymentConfirmationTemplate }}</span>
          </dd>
          <dt>
            <span>Payment Success Template</span>
          </dt>
          <dd>
            <span>{{ thirdPartyIntegration.paymentSuccessTemplate }}</span>
          </dd>
          <dt>
            <span>Payment Error Template</span>
          </dt>
          <dd>
            <span>{{ thirdPartyIntegration.paymentErrorTemplate }}</span>
          </dd>
          <dt>
            <span>Integration Category</span>
          </dt>
          <dd>
            <span>{{ thirdPartyIntegration.integrationCategory }}</span>
          </dd>
          <dt>
            <span>Visiblity</span>
          </dt>
          <dd>
            <span>{{ thirdPartyIntegration.visiblity }}</span>
          </dd>
          <dt>
            <span>Confirm Recipient Identity</span>
          </dt>
          <dd>
            <span>{{ thirdPartyIntegration.confirmRecipientIdentity }}</span>
          </dd>
          <dt>
            <span>Category Menus</span>
          </dt>
          <dd>
            <span v-for="(categoryMenus, i) in thirdPartyIntegration.categoryMenus" :key="categoryMenus.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'MenuView', params: { menuId: categoryMenus.id } }">{{ categoryMenus.id }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
        <router-link
          v-if="thirdPartyIntegration.id"
          :to="{ name: 'ThirdPartyIntegrationEdit', params: { thirdPartyIntegrationId: thirdPartyIntegration.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span>Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./third-party-integration-details.component.ts"></script>
