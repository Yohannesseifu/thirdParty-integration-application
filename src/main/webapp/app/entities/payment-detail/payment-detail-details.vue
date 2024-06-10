<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="paymentDetail">
        <h2 class="jh-entity-heading" data-cy="paymentDetailDetailsHeading"><span>Payment Detail</span> {{ paymentDetail.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Computed Payment Detail</span>
          </dt>
          <dd>
            <span>{{ paymentDetail.computedPaymentDetail }}</span>
          </dd>
          <dt>
            <span>Api Request</span>
          </dt>
          <dd>
            <div v-if="paymentDetail.apiRequest">
              <router-link :to="{ name: 'ApiRequestView', params: { apiRequestId: paymentDetail.apiRequest.id } }">{{
                paymentDetail.apiRequest.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Operation</span>
          </dt>
          <dd>
            <div v-if="paymentDetail.operation">
              <router-link :to="{ name: 'OperationView', params: { operationId: paymentDetail.operation.id } }">{{
                paymentDetail.operation.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
        <router-link
          v-if="paymentDetail.id"
          :to="{ name: 'PaymentDetailEdit', params: { paymentDetailId: paymentDetail.id } }"
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

<script lang="ts" src="./payment-detail-details.component.ts"></script>
