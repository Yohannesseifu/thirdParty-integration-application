<template>
  <div>
    <h2 id="page-heading" data-cy="PaymentParamHeading">
      <span id="payment-param-heading">Payment Params</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
        <router-link :to="{ name: 'PaymentParamCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-payment-param"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span>Create a new Payment Param</span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && paymentParams && paymentParams.length === 0">
      <span>No Payment Params found</span>
    </div>
    <div class="table-responsive" v-if="paymentParams && paymentParams.length > 0">
      <table class="table table-striped" aria-describedby="paymentParams">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Type</span></th>
            <th scope="row"><span>Name</span></th>
            <th scope="row"><span>Value Str</span></th>
            <th scope="row"><span>Data Type</span></th>
            <th scope="row"><span>Payment Detail</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="paymentParam in paymentParams" :key="paymentParam.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PaymentParamView', params: { paymentParamId: paymentParam.id } }">{{
                paymentParam.id
              }}</router-link>
            </td>
            <td>{{ paymentParam.type }}</td>
            <td>{{ paymentParam.name }}</td>
            <td>{{ paymentParam.valueStr }}</td>
            <td>{{ paymentParam.dataType }}</td>
            <td>
              <div v-if="paymentParam.paymentDetail">
                <router-link :to="{ name: 'PaymentDetailView', params: { paymentDetailId: paymentParam.paymentDetail.id } }">{{
                  paymentParam.paymentDetail.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PaymentParamView', params: { paymentParamId: paymentParam.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PaymentParamEdit', params: { paymentParamId: paymentParam.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(paymentParam)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="thirdPartyIntegrationApplicationApp.paymentParam.delete.question" data-cy="paymentParamDeleteDialogHeading"
          >Confirm delete operation</span
        >
      </template>
      <div class="modal-body">
        <p id="jhi-delete-paymentParam-heading">Are you sure you want to delete Payment Param {{ removeId }}?</p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-paymentParam"
            data-cy="entityConfirmDeleteButton"
            v-on:click="removePaymentParam()"
          >
            Delete
          </button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./payment-param.component.ts"></script>
