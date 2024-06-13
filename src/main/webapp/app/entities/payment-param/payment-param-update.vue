<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2 id="thirdPartyIntegrationApplicationApp.paymentParam.home.createOrEditLabel" data-cy="PaymentParamCreateUpdateHeading">
          Create or edit a Payment Param
        </h2>
        <div>
          <div class="form-group" v-if="paymentParam.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="paymentParam.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="payment-param-type">Type</label>
            <select
              class="form-control"
              name="type"
              :class="{ valid: !v$.type.$invalid, invalid: v$.type.$invalid }"
              v-model="v$.type.$model"
              id="payment-param-type"
              data-cy="type"
            >
              <option v-for="paymentParamType in paymentParamTypeValues" :key="paymentParamType" v-bind:value="paymentParamType">
                {{ paymentParamType }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="payment-param-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="payment-param-name"
              data-cy="name"
              :class="{ valid: !v$.name.$invalid, invalid: v$.name.$invalid }"
              v-model="v$.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="payment-param-valueStr">Value Str</label>
            <input
              type="text"
              class="form-control"
              name="valueStr"
              id="payment-param-valueStr"
              data-cy="valueStr"
              :class="{ valid: !v$.valueStr.$invalid, invalid: v$.valueStr.$invalid }"
              v-model="v$.valueStr.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="payment-param-dataType">Data Type</label>
            <select
              class="form-control"
              name="dataType"
              :class="{ valid: !v$.dataType.$invalid, invalid: v$.dataType.$invalid }"
              v-model="v$.dataType.$model"
              id="payment-param-dataType"
              data-cy="dataType"
              required
            >
              <option v-for="dataType in dataTypeValues" :key="dataType" v-bind:value="dataType">{{ dataType }}</option>
            </select>
            <div v-if="v$.dataType.$anyDirty && v$.dataType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.dataType.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="payment-param-paymentDetail">Payment Detail</label>
            <select
              class="form-control"
              id="payment-param-paymentDetail"
              data-cy="paymentDetail"
              name="paymentDetail"
              v-model="paymentParam.paymentDetail"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  paymentParam.paymentDetail && paymentDetailOption.id === paymentParam.paymentDetail.id
                    ? paymentParam.paymentDetail
                    : paymentDetailOption
                "
                v-for="paymentDetailOption in paymentDetails"
                :key="paymentDetailOption.id"
              >
                {{ paymentDetailOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./payment-param-update.component.ts"></script>
