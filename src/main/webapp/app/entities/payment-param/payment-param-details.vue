<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="paymentParam">
        <h2 class="jh-entity-heading" data-cy="paymentParamDetailsHeading"><span>Payment Param</span> {{ paymentParam.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Type</span>
          </dt>
          <dd>
            <span>{{ paymentParam.type }}</span>
          </dd>
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ paymentParam.name }}</span>
          </dd>
          <dt>
            <span>Value</span>
          </dt>
          <dd>
            <span>{{ paymentParam.value }}</span>
          </dd>
          <dt>
            <span>Data Type</span>
          </dt>
          <dd>
            <span>{{ paymentParam.dataType }}</span>
          </dd>
          <dt>
            <span>Payment Detail</span>
          </dt>
          <dd>
            <div v-if="paymentParam.paymentDetail">
              <router-link :to="{ name: 'PaymentDetailView', params: { paymentDetailId: paymentParam.paymentDetail.id } }">{{
                paymentParam.paymentDetail.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
        <router-link
          v-if="paymentParam.id"
          :to="{ name: 'PaymentParamEdit', params: { paymentParamId: paymentParam.id } }"
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

<script lang="ts" src="./payment-param-details.component.ts"></script>
